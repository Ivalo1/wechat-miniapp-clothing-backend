package com.ivalo1.wechatbackend.application.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.application.vo.CategoryTreeVO;
import com.ivalo1.wechatbackend.domain.product.Category;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List; import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class CategoryAppService {
    private final CategoryMapper categoryMapper;
    public List<CategoryTreeVO> getCategoryTree() {
        List<Category> all = categoryMapper.selectList(new LambdaQueryWrapper<Category>().eq(Category::getStatus, 1).orderByAsc(Category::getSortOrder));
        return buildTree(all, 0L);
    }
    private List<CategoryTreeVO> buildTree(List<Category> all, Long parentId) {
        return all.stream().filter(c -> parentId.equals(c.getParentId())).map(c -> {
            CategoryTreeVO vo = new CategoryTreeVO();
            vo.setId(c.getId()); vo.setName(c.getName()); vo.setIcon(c.getIcon()); vo.setLevel(c.getLevel());
            vo.setChildren(buildTree(all, c.getId()));
            return vo;
        }).collect(Collectors.toList());
    }
}
