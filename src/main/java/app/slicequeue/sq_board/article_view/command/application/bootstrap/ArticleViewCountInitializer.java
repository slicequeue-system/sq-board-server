package app.slicequeue.sq_board.article_view.command.application.bootstrap;

import app.slicequeue.sq_board.article_view.command.application.service.ArticleViewCountBackUpProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleViewCountInitializer implements ApplicationRunner {
    private final ArticleViewCountBackUpProcessor backUpProcessor;

    @Override
    public void run(ApplicationArguments args) {
        // TODO: 대용량 처리일 경우 이 부분 처리 중에 문제 야기 될 수 있음 ()
        //  - 이 부분은 단일 컨테이너 실행에 따라 처리되며 여러개 서버가 띄워질 경우 불필요한 동기화 처리임
        //  - 내부에 전체 조회 처리 때문에 페이징 처리가 되어 있지 않아 문제 발생
        //  - 추후에는 redis 자체 설정에 따라 복구 되는 형태로 처리하는 것이 용이, 또는 배치 잡, 테스크 등으로 서버와 별도 처리하는 것을 추천
        backUpProcessor.restoreViewCounts();
    }
}
