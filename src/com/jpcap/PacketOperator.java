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
		 //获得所有网卡列表
		 NetworkInterface[] devices = JpcapCaptor.getDeviceList();  
//打印网卡信息
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
	        //通过网卡，打开一个捕捉器
	        JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 65535,  
	                false, 20);  
	        // 只接收目的地址是指定IP的数据包
	        //captor.setFilter("dst host 219.234.83.63", true);
	        //只接受源地址是指定IP的数据包
	        captor.setFilter("src host 54.236.135.166", true); 
	        //通过回调函数捕获数据包
	        captor.loopPacket(-1, new Monitor());
//	        Packet pt = null;  
//	        int i = 1;  
//	        while (i == 1) {  
//	            pt = captor.getPacket();  
//	            if (pt != null) {  
//	            	// 判断是否是TCP数据包
//	                if (pt instanceof TCPPacket) {  
//	                    TCPPacket tcpp = (TCPPacket) pt;  
//	                    EthernetPacket ethernetPacket = (EthernetPacket)pt.datalink;
//	                    System.out.println("源IP："+tcpp.src_ip+"目的IP："+tcpp.dst_ip);
//	                    System.out.println("源端口："+tcpp.src_port+"目的端口："+tcpp.dst_port);
//	                    System.out.println("源mac地址："+ethernetPacket.getSourceAddress()+"目的mac地址："+ethernetPacket.getDestinationAddress());
//	                    System.out.println("协议："+tcpp.protocol);
//	                    System.out.println("数据:");
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
	          	// 判断是否是TCP数据包
	              if (pt instanceof TCPPacket) {  
	                  TCPPacket tcpp = (TCPPacket) pt;  
	                  EthernetPacket ethernetPacket = (EthernetPacket)pt.datalink;
	                  System.out.println("源IP："+tcpp.src_ip+"目的IP："+tcpp.dst_ip);
	                  System.out.println("源端口："+tcpp.src_port+"目的端口："+tcpp.dst_port);
	                  System.out.println("源mac地址："+ethernetPacket.getSourceAddress()+"目的mac地址："+ethernetPacket.getDestinationAddress());
	                  System.out.println("协议："+tcpp.protocol);
	                  System.out.println("数据:");
	                  for(int j=0;j<tcpp.data.length;j++)
	                  {
	                  	System.out.print((char)tcpp.data[j]);
	                  }
	                  //System.out.println(tcpp);  
	              }  
	          }  
	}

}
