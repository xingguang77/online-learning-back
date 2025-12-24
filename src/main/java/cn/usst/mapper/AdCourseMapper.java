package cn.usst.mapper;
import cn.usst.pojo.Course;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface AdCourseMapper {
    void insert(Course course);
    void deleteById(Long id);
    void update(Course course);
    List<Course> list(String name); // 支持按名称搜索
    Course findById(Long id);
}