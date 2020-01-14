package instagram2.imagecatalog.services.log;


import com.kumuluz.ee.common.config.EeConfig;
import com.kumuluz.ee.common.runtime.EeRuntime;
import com.kumuluz.ee.logs.cdi.Log;
import org.apache.logging.log4j.CloseableThreadContext;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.HashMap;
import java.util.UUID;

@Log
@Interceptor
@Priority(Interceptor.Priority.PLATFORM_BEFORE)
public class LogContextInterceptor {


    @AroundInvoke
    public Object logMethodEntryAndExit(InvocationContext context) throws Exception {

        HashMap settings = new HashMap();

        settings.put("applicationName", EeConfig.getInstance().getName());
        settings.put("applicationVersion", EeConfig.getInstance().getVersion());
        settings.put("environmentType", EeConfig.getInstance().getEnv().getName());
        settings.put("uniqueInstanceId", EeRuntime.getInstance().getInstanceId());
        settings.put("uniqueRequestId", UUID.randomUUID().toString());

        try (final CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(settings)) {
            return context.proceed();
        }
    }
}
