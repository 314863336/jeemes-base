package com.huitai.search.service;

import com.huitai.search.entity.EsDemo;
import com.huitai.search.repository.EsDemoRepository;
import com.huitai.search.base.BaseElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * description: EsDemoService <br>
 * date: 2020/4/22 15:44 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
@Service
public class EsDemoService extends BaseElasticsearchService {

    @Autowired
    private EsDemoRepository esDemoRepository;

    public void save(EsDemo esDemo) {
        esDemoRepository.save(esDemo);
    }

    public EsDemo findById(String id) {
        Optional<EsDemo> byId = esDemoRepository.findById(id);
        return byId.get();
    }

    public List<EsDemo> findAll() {
        Iterable<EsDemo> result = esDemoRepository.findAll();
        Iterator<EsDemo> resu = result.iterator();
        List<EsDemo> list = new ArrayList<>();
        while (resu.hasNext()){
            list.add(resu.next());
        }
        return list;
    }

    public void deleteById(String id) {
        esDemoRepository.deleteById(id);
    }

    public void deleteAll() {
        esDemoRepository.deleteAll();
    }
}
