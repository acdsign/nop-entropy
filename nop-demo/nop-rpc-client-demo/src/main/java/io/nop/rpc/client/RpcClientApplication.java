package io.nop.rpc.client;

import io.nop.boot.NopApplication;
import io.nop.core.initialize.CoreInitialization;
import io.nop.quarkus.core.QuarkusIntegration;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.QuarkusMain;

import javax.enterprise.event.Observes;

@QuarkusMain
public class RpcClientApplication {
    static String[] globalArgs;

    public void start(@Observes StartupEvent event) {
        QuarkusIntegration.start();
        new NopApplication().run(globalArgs);
    }

    public void stop(@Observes ShutdownEvent event) {
        CoreInitialization.destroy();
    }

    public static void main(String... args) {
        globalArgs = args;
        Quarkus.run(args);
    }
}
