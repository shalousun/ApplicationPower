# PrettyMemoryUtil介绍
该工具用于计算内存的占用大小，该工具根据文件的大小个性化显示，所属的包为com.power.common.util。

## 1. prettyByteSize(long byteSize)方法
根据传入的字节数，友情显示内存大小

Usage:
```
private static final int UNIT = 1024;
PrettyMemoryUtil.prettyByteSize(1023);//return 1023B
PrettyMemoryUtil.prettyByteSize(1L * UNIT);//return 1KB
PrettyMemoryUtil.prettyByteSize(1L * UNIT * UNIT);//return 1MB
```