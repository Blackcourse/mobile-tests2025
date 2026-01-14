package configs;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:mobile.properties",
        "classpath:${platform}.properties"
          })
@Config.LoadPolicy(Config.LoadType.MERGE)
public interface MobileConfig extends Config {

    @Key ("platform")
    String platform();

    @Key ("os_version")
    String os_version();

    @Key("device")
    String device();

    @Key("browserstackUser")
    String browserstackUser();

    @Key("browserstackKey")
    String browserstackKey();


}
