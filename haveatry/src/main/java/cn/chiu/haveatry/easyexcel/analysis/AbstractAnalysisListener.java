package cn.chiu.haveatry.easyexcel.analysis;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 1. 抽象ExcelModel,返回数据集
 * 2. 支持最大行数校验
 * 3. 模板校验
 * 4. 根据自身业务需要，处理异常
 * 5. 支持解析中断
 *
 * @anther yeachiu
 * @date 2021-09-02
 */
public abstract class AbstractAnalysisListener<T, V> extends AnalysisEventListener<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAnalysisListener.class);

    protected List<V> dataList;

    protected int maxDataRows;

    protected String curSheetName;

    private boolean firstBlood = true;

    private boolean isInterrupt = false;

    private ExcelAnalysisResult<V> analysisResult;

    public AbstractAnalysisListener(String sheetName, int maxDataRows) {
        this.maxDataRows = maxDataRows;
        this.curSheetName = sheetName;
    }

    /**
     * 处理 rowData 到 ExcelModel 的转换
     * @param rowData
     */
    public abstract void invoke(T rowData);

    /**
     * return excel analysis result
     *
     * @return analysisResult
     */
    public ExcelAnalysisResult<V> getAnalysisResult() {
        return analysisResult;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        if (isInterrupt) {
            LOGGER.error("解析已中断");
            return;
        }
        // 第一次解析数据，执行模板级的校验
        if (firstBlood) {
            firstBlood = false;

            // 模板校验
            if (!context.readSheetHolder().getSheetName().equals(curSheetName)) {
                interrupt(ImportExportErrorCode.CAN_NOT_READ);
            }

            // 总行数限制校验
            int actualRowNumber = context.readSheetHolder().getApproximateTotalRowNumber();
            if (actualRowNumber > maxDataRows) {
                interrupt(ImportExportErrorCode.EXCEED_ROW_LIMIT);
            }
        }
        invoke(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        analysisResult.isSuccess = true;
        analysisResult.data = dataList;
    }

    /**
     * 自定义异常处理
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        interrupt(ImportExportErrorCode.INTERNAL_SERVER_ERROR);
        LOGGER.error("解析失败，中断解析下一行:{}", exception.getMessage());

        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            LOGGER.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex());
        }

    }

    /**
     * 重写方法，客户端控制解析中断
     *
     * @param context Analysis Context
     * @return Whether to continue analysis
     */
    @Override
    public boolean hasNext(AnalysisContext context) {
        return !isInterrupt;
    }

    private void interrupt(ImportExportErrorCode errorCode) {
        this.isInterrupt = true;
        analysisResult.isSuccess = false;
        analysisResult.errorCode = errorCode;
    }

    @Data
    class ExcelAnalysisResult<E> {
        private boolean isSuccess;
        private ImportExportErrorCode errorCode;
        private List<E> data;
    }

    enum ImportExportErrorCode {
        CAN_NOT_READ(7001, "File Can Not Read"),
        EXCEED_ROW_LIMIT(7002, "Exceed Row Limit"),
        INTERNAL_SERVER_ERROR(7003, "Internal Server Error");

        ImportExportErrorCode(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private int code;
        private String desc;
    }
}
