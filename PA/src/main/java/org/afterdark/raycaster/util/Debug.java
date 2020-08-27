package org.afterdark.raycaster.util;

public class Debug
{

    public static void logError(String Error)
    {
        log("[ERROR!!]" + Error);
    }

    public static void logWarning(String Warn)
    {
        log("[WARNING]" + Warn);
    }

    public static void log(String log)
    {
        System.out.println(log);
    }
}
