package cn.chiu.haveatry.pcap.dto;

import lombok.Data;

/**
 * 文件头模型，共占24字节
 * @author yeachiu
 * @date 2023/3/22
 */
@Data
public class PcapHeader {
    private byte[] magic; // 4B
    private byte[] majorVersion; // 2B
    private byte[] minorVersion; // 2B
    private byte[] thisZone; // 4B
    private byte[] sigFlgs; // 4B
    private byte[] snapLen; // 4B
    private byte[] linkType; // 4B


}
