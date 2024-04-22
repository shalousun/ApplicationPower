package com.power.common.util;

import java.util.*;

/**
 * @author yu 2020/8/18.
 */
public class Test {

    public static void main(String[] args) {
        String[] cpuInfo = {"fpu", "vme", "de", "pse", "tsc", "msr", "pae", "mce", "cx8", "apic", "sep", "mtrr", "pge", "mca", "cmov", "pat", "pse36", "clflush", "dts", "acpi", "mmx", "fxsr", "sse", "sse2", "ss", "ht", "tm", "pbe", "syscall", "nx", "pdpe1gb", "rdtscp", "lm", "constant_tsc", "art", "arch_perfmon", "pebs", "bts", "rep_good", "nopl", "xtopology", "nonstop_tsc", "cpuid", "aperfmperf", "pni", "pclmulqdq", "dtes64", "monitor", "ds_cpl", "vmx", "smx", "est", "tm2", "ssse3", "sdbg", "fma", "cx16", "xtpr", "pdcm", "pcid", "dca", "sse4_1", "sse4_2", "x2apic", "movbe", "popcnt", "tsc_deadline_timer", "aes", "xsave", "avx", "f16c", "rdrand", "lahf_lm", "abm", "3dnowprefetch", "cpuid_fault", "epb", "cat_l3", "cdp_l3", "invpcid_single", "pti", "intel_ppin", "mba", "ibrs", "ibpb", "stibp", "tpr_shadow", "vnmi", "flexpriority", "ept", "vpid", "fsgsbase", "tsc_adjust", "bmi1", "hle", "avx2", "smep", "bmi2", "erms", "invpcid", "rtm", "cqm", "mpx", "rdt_a", "avx512f", "avx512dq", "rdseed", "adx", "smap", "clflushopt", "clwb", "intel_pt", "avx512cd", "avx512bw", "avx512vl", "xsaveopt", "xsavec", "xgetbv1", "xsaves", "cqm_llc", "cqm_occup_llc", "cqm_mbm_total", "cqm_mbm_local", "dtherm", "ida", "arat", "pln", "pts", "pku", "ospke"};
        String[] cpuInfo2 = {"fpu", "vme", "de", "pse", "tsc", "msr", "pae", "mce", "cx8", "apic", "sep", "mtrr", "pge", "mca", "cmov", "pat", "pse36", "clflush", "dts", "acpi", "mmx", "fxsr", "sse", "sse2", "ss", "ht", "tm", "pbe", "syscall", "nx", "pdpe1gb", "rdtscp", "lm", "constant_tsc", "arch_perfmon", "pebs", "bts", "rep_good", "nopl", "xtopology", "nonstop_tsc", "cpuid", "aperfmperf", "pni", "pclmulqdq", "dtes64", "monitor", "ds_cpl", "vmx", "smx", "est", "tm2", "ssse3", "cx16", "xtpr", "pdcm", "pcid", "dca", "sse4_1", "sse4_2", "x2apic", "popcnt", "tsc_deadline_timer", "aes", "xsave", "avx", "f16c", "rdrand", "lahf_lm", "cpuid_fault", "pti", "tpr_shadow", "vnmi", "flexpriority", "ept", "vpid", "fsgsbase", "smep", "erms", "xsaveopt", "dtherm", "ida", "arat", "pln", "pts"};

        List<String>  list1 = new ArrayList<>(Arrays.asList(cpuInfo));
        List<String> list2 = new ArrayList<>(Arrays.asList(cpuInfo2));

        // 找出list1有而list2没有的元素
        list1.removeAll(list2);
        System.out.println("List1 has, but List2 doesn't have: " + list1);

        // 找出list2有而list1没有的元素
        list2.removeAll(new ArrayList<>(list1)); // 创建list1的副本以避免改变list1
        System.out.println("List2 has, but List1 doesn't have: " + list2);
    }


    public static void countQueue(int personNumber, int number) {
        //1.把人放到队列中
        Queue<Integer> persons = new LinkedList<Integer>();
        for (int i = 0; i < personNumber; i++) {
            persons.add(i + 1);
        }

        //2.算法
        int counts = 0;//计数器
        while (!persons.isEmpty() && persons.size() > 2) {
            //1.出队列
            Integer person = persons.poll();
            //2.计数器++
            counts++;
            //3.判断
            if (counts % number == 0) {
                //是,打印
                // System.out.println(person);
            } else {
                //不是,继续入队列
                persons.add(person);
            }
            if (counts == 100) {
                persons.forEach(i -> System.out.println(i));
            }
        }
        // persons.forEach(i->System.out.println(i));
    }

}
