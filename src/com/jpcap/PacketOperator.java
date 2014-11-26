package com.jpcap;


import java.io.IOException;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.PacketReceiver;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;;

public class PacketOperator {
	public static void main(String[] args) throws IOException
	{
		 //������������б�
		 NetworkInterface[] devices = JpcapCaptor.getDeviceList();  
//��ӡ������Ϣ
	        for (int i = 0; i < devices.length; i++) {  
	            System.out.println(i + ": " + devices[i].name + "("  
	                    + devices[i].description + ")");  
	            System.out.println(" datalink: " + devices[i].datalink_name + "("  
	                    + devices[i].datalink_description + ")");  
	            System.out.print(" MAC address:");  
	            for (byte b : devices[i].mac_address)  
	                System.out.print(Integer.toHexString(b & 0xff) + ":");  
	            System.out.println();  
	            for (NetworkInterfaceAddress a : devices[i].addresses)  
	                System.out.println(" address:" + a.address + " " + a.subnet  
	                        + " " + a.broadcast);  
	        }  
	        int index = 2;  
	        //ͨ����������һ����׽��
	        JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 65535,  
	                false, 20);  
	        // ֻ����Ŀ�ĵ�ַ��ָ��IP�����ݰ�
	        //captor.setFilter("dst host 219.234.83.63", true);
	        //ֻ����Դ��ַ��ָ��IP�����ݰ�
	        captor.setFilter("src host 54.236.135.166", true); 
	        //ͨ���ص������������ݰ�
	        captor.loopPacket(-1, new Monitor());
//	        Packet pt = null;  
//	        int i = 1;  
//	        while (i == 1) {  
//	            pt = captor.getPacket();  
//	            if (pt != null) {  
//	            	// �ж��Ƿ���TCP���ݰ�
//	                if (pt instanceof TCPPacket) {  
//	                    TCPPacket tcpp = (TCPPacket) pt;  
//	                    EthernetPacket ethernetPacket = (EthernetPacket)pt.datalink;
//	                    System.out.println("ԴIP��"+tcpp.src_ip+"Ŀ��IP��"+tcpp.dst_ip);
//	                    System.out.println("Դ�˿ڣ�"+tcpp.src_port+"Ŀ�Ķ˿ڣ�"+tcpp.dst_port);
//	                    System.out.println("Դmac��ַ��"+ethernetPacket.getSourceAddress()+"Ŀ��mac��ַ��"+ethernetPacket.getDestinationAddress());
//	                    System.out.println("Э�飺"+tcpp.protocol);
//	                    System.out.println("����:");
//	                    for(int j=0;j<tcpp.data.length;j++)
//	                    {
//	                    	System.out.print((char)tcpp.data[j]);
//	                    }
//	                    //System.out.println(tcpp);  
//	                }  
//	            }  
//	        }  
	        captor.close();  
	    }  
	
}
class Monitor implements PacketReceiver
{

	@Override
	public void receivePacket(Packet pt) {
		// TODO Auto-generated method stub
		 if (pt != null) {  
	          	// �ж��Ƿ���TCP���ݰ�
	              if (pt instanceof TCPPacket) {  
	                  TCPPacket tcpp = (TCPPacket) pt;  
	                  EthernetPacket ethernetPacket = (EthernetPacket)pt.datalink;
	                  System.out.println("ԴIP��"+tcpp.src_ip+"Ŀ��IP��"+tcpp.dst_ip);
	                  System.out.println("Դ�˿ڣ�"+tcpp.src_port+"Ŀ�Ķ˿ڣ�"+tcpp.dst_port);
	                  System.out.println("Դmac��ַ��"+ethernetPacket.getSourceAddress()+"Ŀ��mac��ַ��"+ethernetPacket.getDestinationAddress());
	                  System.out.println("Э�飺"+tcpp.protocol);
	                  System.out.println("����:");
	                  for(int j=0;j<tcpp.data.length;j++)
	                  {
	                  	System.out.print((char)tcpp.data[j]);
	                  }
	                  //System.out.println(tcpp);  
	              }  
	          }  
	}

}
