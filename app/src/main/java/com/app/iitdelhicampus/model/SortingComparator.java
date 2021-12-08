package com.app.iitdelhicampus.model;

import java.util.*;
public class SortingComparator implements Comparator<MarkAttendanceModel>{
public int compare(MarkAttendanceModel s1,MarkAttendanceModel s2){
if(s1.startTime==s2.startTime)
return 0;  
else if(s1.startTime<s2.startTime)
return 1;  
else  
return -1;  
}  
}  