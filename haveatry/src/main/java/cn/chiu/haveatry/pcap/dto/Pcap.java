package cn.chiu.haveatry.pcap.dto;

import lombok.Data;

import java.util.List;

/**
 * 链路层的数据帧
 *
 * @author yeachiu
 * @date 2023/3/22
 */
@Data
public class Pcap {
    private PcapHeader header;
    private List<PcapData> data;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("header{\n");
        sb.append(header.toString());
        sb.append("}\n");
        sb.append("data part count=").append(data.size());
        return sb.toString();
    }
}
