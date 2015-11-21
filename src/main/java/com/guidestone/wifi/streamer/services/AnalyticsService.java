package com.guidestone.wifi.streamer.services;

import com.guidestone.wifi.streamer.domain.C;
import com.guidestone.wifi.streamer.domain.Col;
import com.guidestone.wifi.streamer.domain.GraphData;
import com.guidestone.wifi.streamer.domain.Row;
import com.guidestone.wifi.streamer.entities.AnalyticsEntity;
import com.guidestone.wifi.streamer.repositories.AnalyticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class AnalyticsService {

    private static final Logger LOG = LoggerFactory.getLogger(AnalyticsService.class);

    @Autowired
    private AnalyticsRepository analyticsRepository;

    public Page<AnalyticsEntity> getAnalyticsForMedia(Integer mediaId, Pageable pageable) {
        return analyticsRepository.findByMediaId(mediaId, pageable);
    }

    public Page<AnalyticsEntity> getAnalyticsForUser(Integer userId, Pageable pageable) {
        return analyticsRepository.findByUserId(userId, pageable);
    }

    public Object addAnalytics(List<AnalyticsEntity> analytics) {
        return analyticsRepository.save(analytics);
    }

    public GraphData fetchMediaCategoryCount() {
        GraphData graphData = new GraphData();
        List<Row> rows = new ArrayList<>();
        graphData.setCols(getCols());
        List<Object[]> mediaCategoryCounts = analyticsRepository.fetchMediaCategoryCount();
        for(Object[] o : mediaCategoryCounts) {
            LOG.info("mediaCategoryCounts is {}", o);
            Row row = getRow(String.valueOf(o[1]), String.valueOf(o[0]));
            rows.add(row);
        }
        graphData.setRows(rows);
        return graphData;
    }

    public GraphData fetchTopViewedMedia() {
        GraphData graphData = new GraphData();
        List<Row> rows = new ArrayList<>();
        graphData.setCols(getCols());
        List<Object[]> topViewedMedia = analyticsRepository.fetchTopViewedMedia();
        for(Object[] o : topViewedMedia) {
            LOG.info("topViewedMedia is {}", o);
            Row row = getRow(String.valueOf(o[0]), String.valueOf(o[1]));
            rows.add(row);
        }
        graphData.setRows(rows);
        return graphData;
    }

    private List<Col> getCols() {
        return Arrays.asList(new Col[]{getCol("Topping", "string"), getCol("Slices", "number")});
    }

    private Col getCol(String label, String type) {
        Col col = new Col();
        col.setLabel(label);
        col.setType(type);
        return col;
    }

    private List<Row> getRows() {
        return Arrays.asList(new Row[]{getRow(null, null), getRow(null, null)});
    }

    private Row getRow(String v1, String v2) {
        Row row = new Row();
        row.setC(Arrays.asList(new C[]{getC(v1), getC(v2)}));
        return row;
    }

    private C getC(String v) {
        C c = new C();
        c.setV(v);
        return c;
    }

}