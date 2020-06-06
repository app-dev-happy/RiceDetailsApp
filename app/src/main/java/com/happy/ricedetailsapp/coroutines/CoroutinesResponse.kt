package com.happy.ricedetailsapp.coroutines;

import android.os.Bundle
import java.io.Serializable

public class CoroutinesResponse :Serializable{
    var responseEntity:Map<String, Any>?=null
    var responseEntityList:List<Map<String, Any>>?=null
    var status:Int=0;
    var bundle:Bundle?=null
    var dataString:String?=null
}