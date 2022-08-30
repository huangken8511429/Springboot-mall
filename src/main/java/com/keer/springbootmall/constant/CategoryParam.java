package com.keer.springbootmall.constant;

public class CategoryParam {

  private ProductCategory category;
  private String search;

  private String orderBy;

    private String Sort;

    public String getSort() {
        return Sort;
    }

    public void setSort(String sort) {
        Sort = sort;
    }


    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
