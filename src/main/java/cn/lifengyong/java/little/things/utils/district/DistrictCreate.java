package cn.lifengyong.java.little.things.utils.district;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class DistrictCreate {
  public static void main(String[] args) throws IOException {

    //-- 2019年2月中华人民共和国县以上行政区划代码
    //-- http://www.mca.gov.cn/article/sj/xzqh/2019/201901-06/20190203221738.html
    List<String> list = readLines(new File(DistrictCreate.class.getResource("").getPath() + "district.txt"), "utf-8");

    String provinceid = null;
    String cityid = null;

    for (String string : list) {
      District district = new District();
      String id = string.substring(0, 6);
      String name = string.substring(string.indexOf("	") + 1).trim();
      district.setId(id);
      district.setCnName(name);

      if (id.indexOf("0000") > 0) {// ʡ
        district.setLevel("1");
        provinceid = id;
        district.setParentId("0");

        System.out.println(district);
      } else if (id.lastIndexOf("00") == 4 // 重庆的是500100 500101, 省辖市没有一定的规律
          || "419001".equals(id) // 济源市
          || "429004".equals(id) // 仙桃市
          || "429005".equals(id) // 潜江市
          || "429006".equals(id) // 天门市
          || "429021".equals(id) // 神农架林区
          || "469001".equals(id) // 五指山市
          || "469002".equals(id) // 琼海市
          || "469005".equals(id) // 文昌市
          || "469006".equals(id) // 万宁市
          || "469007".equals(id) // 东方市
          || "469021".equals(id) // 定安县
          || "469022".equals(id) // 屯昌县
          || "469023".equals(id) // 澄迈县
          || "469024".equals(id) // 临高县
          || "469025".equals(id) // 白沙黎族自治县
          || "469026".equals(id) // 昌江黎族自治县
          || "469027".equals(id) // 乐东黎族自治县
          || "469028".equals(id) // 陵水黎族自治县
          || "469029".equals(id) // 保亭黎族苗族自治县
          || "469030".equals(id) // 琼中黎族苗族自治县
          || "659001".equals(id) // 石河子市
          || "659002".equals(id) // 阿拉尔市
          || "659003".equals(id) // 图木舒克市
          || "659004".equals(id) // 五家渠市
          || "659005".equals(id) // 北屯市
          || "659006".equals(id) // 铁门关市
          || "659007".equals(id) // 双河市
          || "659008".equals(id) // 可克达拉市
          || "659009".equals(id) // 昆玉市
      ) {
        district.setLevel("2");
        district.setParentId(provinceid);
        cityid = id;

        System.out.println(district);
      } else {
        district.setLevel("3");
        district.setParentId(cityid);
        System.out.println(district);
      }

    }

  }

  public static List<String> readLines(File file, String encoding) throws IOException {
    try (InputStream in = openInputStream(file)) {
      return readLines(in, encoding == null ? Charset.defaultCharset() : Charset.forName(encoding));
    }
  }

  public static FileInputStream openInputStream(File file) throws IOException {
    if (file.exists()) {
      if (file.isDirectory()) {
        throw new IOException("File '" + file + "' exists but is a directory");
      }
      if (!file.canRead()) {
        throw new IOException("File '" + file + "' cannot be read");
      }
    } else {
      throw new FileNotFoundException("File '" + file + "' does not exist");
    }

    return new FileInputStream(file);
  }

  public static List<String> readLines(InputStream input, Charset encoding) throws IOException {
    InputStreamReader reader = new InputStreamReader(input, encoding);
    return readLines(reader);
  }

  public static List<String> readLines(Reader input) throws IOException {
    BufferedReader reader = new BufferedReader(input);
    List<String> list = new ArrayList<String>();
    String line = reader.readLine();
    while (line != null) {
      list.add(line);
      line = reader.readLine();
    }

    return list;
  }
}
