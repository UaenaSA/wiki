package com.microcore.jcf.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author leizhenyang
 */
public class BusinessModuleConfig
{
    public static Map<Class, String> modules = new ConcurrentHashMap<>();
}
