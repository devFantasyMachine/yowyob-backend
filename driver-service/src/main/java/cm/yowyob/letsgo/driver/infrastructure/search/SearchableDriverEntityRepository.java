package cm.yowyob.letsgo.driver.infrastructure.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchableDriverEntityRepository extends ElasticsearchRepository<SearchableDriverEntity, String> {
}
