package com.keer.yudaovue.framework.common.util.monitor;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;

/**
 * 链路追踪工具类
 *
 * <p>考虑到每个 starter 都需要用到该工具类，所以放到 common 模块下的 util 包下
 *
 * @author keer
 * @date 2024-04-20
 */
public class TracerUtils {

  /** 私有化构造方法 */
  private TracerUtils() {}

  /**
   * 获得链路追踪编号，直接返回 SkyWalking 的 TraceId。
   *
   * <p>如果不存在的话为空字符串！！！
   *
   * @return
   */
  public static String getTraceId() {
    return TraceContext.traceId();
  }
}
