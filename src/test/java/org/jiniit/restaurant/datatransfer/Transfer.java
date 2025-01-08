package org.jiniit.restaurant.datatransfer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.junit.jupiter.api.Test;
import org.locationtech.proj4j.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class Transfer {

    @Test
    void process() {
        CsvMapper mapper = new CsvMapper(); // 의존성 잭슨 추가해서 사용할 수 있는 mapper

        try (BufferedReader br = new BufferedReader(new FileReader("C:/data/data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) { // 한줄한줄 읽어오기
                // System.out.println(line);
                // 구미산동점","2022-11-22 10:24:32","U","2022-11-24 02:40:00","경양식","328608.315535124","295867.827897688","경양  이런식으로 데이터가 추출되기 때문에 list로 가져올거임
                try {
                    List<String> item = mapper.readValue(line, new TypeReference<>() {
                    });
                    if (item.get(8).equals("폐업")) continue;

                    // double lat = Double.parseDouble(item.get(26));
                    // double lon = Double.parseDouble(item.get(27));

                    double[] location = transformTMToWGS84(Double.parseDouble(item.get(26)), Double.parseDouble(item.get(27)));

                    System.out.println(item);
                    System.out.println(Arrays.toString(location));
                    System.out.println("------------------------------------------");

//                    System.out.println("lat=%s, lon=%s%n", lat, lon);
                } catch (Exception e) {}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double[] transformTMToWGS84(double lon, double lat) {
        CRSFactory crsFactory = new CRSFactory();

        // WGS84 좌표계 (EPSG:4326)
        CoordinateReferenceSystem crsWGS84 = crsFactory.createFromName("EPSG:4326");

        // TM 중부원점 좌표계 (EPSG:2097)
        CoordinateReferenceSystem crsTM = crsFactory.createFromParameters("EPSG:2097", "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=1 +x_0=200000 +y_0=500000 +ellps=GRS80 +units=m +no_defs");

        // CoordinateTransformFactory 생성
        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();

        // 좌표 변환 객체 생성
        //CoordinateTransform transform = ctFactory.createTransform(crsWGS84, crsTM);
        CoordinateTransform transform = ctFactory.createTransform(crsTM, crsWGS84);
        // 변환할 좌표 설정
        ProjCoordinate sourceCoordinate = new ProjCoordinate(lon, lat);
        ProjCoordinate targetCoordinate = new ProjCoordinate();

        // 좌표 변환 수행
        transform.transform(sourceCoordinate, targetCoordinate);

        return new double[]{targetCoordinate.x, targetCoordinate.y};
    }
}