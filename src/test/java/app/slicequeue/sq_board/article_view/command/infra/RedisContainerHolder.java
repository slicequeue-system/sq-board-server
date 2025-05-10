package app.slicequeue.sq_board.article_view.command.infra;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.GenericContainer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.testcontainers.utility.DockerImageName;

@Component
public class RedisContainerHolder {

    private final GenericContainer<?> redisContainer =
            new GenericContainer<>(DockerImageName.parse("redis:7.2.3"))
                    .withExposedPorts(6379)
                    .withCreateContainerCmdModifier(cmd ->
                            cmd.withPortBindings(new PortBinding(Ports.Binding.bindPort(6480), new ExposedPort(6379))))
                    .withReuse(true);

    @PostConstruct
    public void start() {
        redisContainer.start();
    }

    @PreDestroy
    public void stop() {
        redisContainer.stop();
    }

    public String getHost() {
        return redisContainer.getHost();
    }

    public int getPort() {
        return redisContainer.getMappedPort(6379);
    }
}
