package wickeddevs.easywars.dagger.scope;

/**
 * Created by hicke_000 on 8/2/2016.
 */
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Retention(RetentionPolicy.RUNTIME)
@Scope
public @interface ActivityScope {
}
