package cn.chiu.haveatry.pcap.dto;

import lombok.Data;

/**
 * 文件头模型，共占24字节
 * @author yeachiu
 * @date 2023/3/22
 */
@Data
public class PcapHeader {
    private int magic; // 4B
    private short majorVersion; // 2B
    private int minorVersion; // 2B
    private int thisZone; // 4B
    private int sigFlgs; // 4B
    private int snapLen; // 4B
    private int linkType; // 4B
}
