import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import pl.reszy.filestorage.FileStorageApplication
import pl.reszy.filestorage.controller.StatsController
import pl.reszy.filestorage.service.FileRepository
import spock.lang.Specification

@ActiveProfiles('test')
@SpringBootTest(classes = FileStorageApplication.class, properties = '')
class IntegrationSpec extends Specification {

    @Autowired(required = false)
    private StatsController controller

    @MockBean
    private FileRepository fileRepository

    def 'should get up'() {
        expect: 'StatsController is created'
        controller
    }

    def 'should get statistics'() {
        when:
        def result = controller.getStatsDto()

        then:
        result.filesStored == 0
        result.occupiedSpace.megabytes == 0
    }
}
