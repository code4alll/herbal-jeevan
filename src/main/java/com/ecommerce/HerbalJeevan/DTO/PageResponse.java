package com.ecommerce.HerbalJeevan.DTO;


import java.util.List;

import org.springframework.data.domain.Page;



public class PageResponse<T> {
	    private List<?> data;
	    private Integer currentPage;
	    private Long totalItems;
	    private Integer totalPages;
	    private Boolean hasNext;
	    private Boolean hasPrevious;
	    private Boolean isFirst;
	    private Boolean isLast;
	    private Integer pageSize;
	    private Sort sort;
	    private Integer numberOfElements; 
	   
	    
	    
	    
	    public PageResponse(Page<T> page) {
	        this.data = page.getContent();
	        this.currentPage = page.getNumber();
	        this.totalItems = page.getTotalElements();
	        this.totalPages = page.getTotalPages();
	        this.hasNext = page.hasNext();
	        this.hasPrevious = page.hasPrevious();
	        this.isFirst = page.isFirst();
	        this.isLast = page.isLast();
	        this.pageSize = page.getSize();
	        this.sort = new Sort(page.getSort().isSorted(), page.getSort().isUnsorted(), page.getSort().isEmpty());
	        this.numberOfElements = page.getNumberOfElements();
	    }
	    
	    
	  
	    
	    public PageResponse(Page<T> page,String type) {
//	        this.data = page.getContent();
	        this.currentPage = page.getNumber();
	        this.totalItems = page.getTotalElements();
	        this.totalPages = page.getTotalPages();
	        this.hasNext = page.hasNext();
	        this.hasPrevious = page.hasPrevious();
	        this.isFirst = page.isFirst();
	        this.isLast = page.isLast();
	        this.pageSize = page.getSize();
	        this.sort = new Sort(page.getSort().isSorted(), page.getSort().isUnsorted(), page.getSort().isEmpty());
	        this.numberOfElements = page.getNumberOfElements();
	    }
	    
	    
	    
	    
	    public List<?> getData() {
			return data;
		}



		public void setData(List<T> data) {
			this.data = data;
		}



		public Integer getCurrentPage() {
			return currentPage;
		}



		public void setCurrentPage(Integer currentPage) {
			this.currentPage = currentPage;
		}



		public Long getTotalItems() {
			return totalItems;
		}



		public void setTotalItems(Long totalItems) {
			this.totalItems = totalItems;
		}



		public Integer getTotalPages() {
			return totalPages;
		}



		public void setTotalPages(Integer totalPages) {
			this.totalPages = totalPages;
		}



		public Boolean getHasNext() {
			return hasNext;
		}



		public void setHasNext(Boolean hasNext) {
			this.hasNext = hasNext;
		}



		public Boolean getHasPrevious() {
			return hasPrevious;
		}



		public void setHasPrevious(Boolean hasPrevious) {
			this.hasPrevious = hasPrevious;
		}



		public Boolean getIsFirst() {
			return isFirst;
		}



		public void setIsFirst(Boolean isFirst) {
			this.isFirst = isFirst;
		}



		public Boolean getIsLast() {
			return isLast;
		}



		public void setIsLast(Boolean isLast) {
			this.isLast = isLast;
		}



		public Integer getPageSize() {
			return pageSize;
		}



		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}



		public Sort getSort() {
			return sort;
		}



		public void setSort(Sort sort) {
			this.sort = sort;
		}



		public Integer getNumberOfElements() {
			return numberOfElements;
		}



		public void setNumberOfElements(Integer numberOfElements) {
			this.numberOfElements = numberOfElements;
		}



		
	
	    

		public static class Sort {
	        private boolean sorted;
	        private boolean unsorted;
	        private boolean empty;
	        
	        public Sort(boolean sorted, boolean unsorted, boolean empty) {
	            this.sorted = sorted;
	            this.unsorted = unsorted;
	            this.empty = empty;
	        }
	        
			public boolean isSorted() {
				return sorted;
			}
			public void setSorted(boolean sorted) {
				this.sorted = sorted;
			}
			public boolean isUnsorted() {
				return unsorted;
			}
			public void setUnsorted(boolean unsorted) {
				this.unsorted = unsorted;
			}
			public boolean isEmpty() {
				return empty;
			}
			public void setEmpty(boolean empty) {
				this.empty = empty;
			}
	        
	        
	        }
}
