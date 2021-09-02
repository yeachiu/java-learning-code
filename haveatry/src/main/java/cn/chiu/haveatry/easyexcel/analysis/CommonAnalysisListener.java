package cn.chiu.haveatry.easyexcel.analysis;

/**
 * Excel file analyser
 *
 * @author raza
 * @date 2021/9/3
 */
public class CommonAnalysisListener<T> extends AbstractAnalysisListener<T, T> {
    public CommonAnalysisListener(String sheetName, int maxDataRows) {
        super(sheetName, maxDataRows);
    }

    @Override
    public void invoke(T rowData) {
        dataList.add(rowData);
    }
}
