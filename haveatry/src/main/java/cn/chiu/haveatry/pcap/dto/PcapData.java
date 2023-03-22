package cn.chiu.haveatry.pcap.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 文件头模型，共占24字节
 * @author yeachiu
 * @date 2023/3/22
 */
@ToString
@Data
public class PcapData {
    /**
     * 时间戳高位，精确到seconds，这是Unix时间戳
     * 捕获数据包的时间一般是根据这个值
     */
    private byte[] timestampHigh; // 4B
    /**
     * 时间戳低位，精确到microseconds
     */
    private byte[] timestampLow; // 4B
    /**
     * 当前数据区长度，即抓取到的数据帧长度
     * 可以由此得到下一个数据帧的位置
     */
    private byte[] capLen; // 4B
    /**
     * 离线数据长度，网络中实际数据帧的长度，一般不大于caplen,多数情况下个caplen一样
     */
    private byte[] len; // 4B
    /**
     * 数据帧
     */
    private byte[] data;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("timestampHigh: ").append(this.timestampHigh).append(",\n");
        sb.append("timestampLow: ").append(this.timestampLow).append(",\n");
        sb.append("capLen: ").append(this.capLen).append(",\n");
        sb.append("len: ").append(this.len).append(",\n");
        sb.append("data: ").append(this.data).append("\n");
        return sb.toString();
    }
}
