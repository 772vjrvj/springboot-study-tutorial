package com.example.springbootstudybasictest.util;

public class PageObject {
    private int perPageRow;           // 페이지당 보여지는 row의 갯수
    private int startRow, endRow;     // 현재 페이지의 시작 row번호, row번호
    private int totalRow;             // 전체 row수 DB에서 구한다.
    private int page;                 // 현재 페이지
    private int perGroupPage;         // 화면에 나오는 페이지 수
    private int startPage;            // 시작 페이지
    private int endPage;              // 끝 페이지
    private int totalPage;            // 전체 페이지
    private boolean prevPage;         // 이전 페이지 존재 여부
    private boolean nextPage;         // 다음 페이지 존재 여부
    private boolean firstPage;        // 처음 페이지 존재 여부
    private boolean lastPage;         // 마지막 페이지 존재 여부

    public PageObject(int page, int perPageRow, int perGroupPage) {
        // 현재 화면의 페이지 번호
        this.page = page;
        this.perPageRow = perPageRow;
        // 시작 row번호와 끝 row번호 계산
        //this.startRow = (page - 1)*perPageRow + 1;  getter 에 넣는다.
        //this.endRow = startRow + perPageRow -1;     getter 에 넣는다.

        // 하단 부분에 몇개의 페이지를 표시할지 정한다.
        this.perGroupPage = perGroupPage;
    }

    // Spring에서는 파라메터 값을 전달 받을때 기본 생성자와 setter()를 이용해서 데이터를 전달 받는다.
    public PageObject() {
        // 처음 리스트로 들어올 때는 데이터가 안 넘어 오므로 초기값을 1페이지에
        // 한페이당 10개의 글을 보이도록 셋팅한다.
        this.page = 1;
        this.perPageRow = 10;
        // 하단 부분에 몇개의 페이지를 표시할지 정한다.
        this.startPage = 1;
        this.endPage = 1;
        this.perGroupPage = 10;
        // service 프로그램에서 setTotalRow(DB의데이터갯수) 호출을 해야만 한다. 그래야 자동 계산이 된다.
    }


    // 페이지에 따른 데이터의 startRow와 endRow, totalPage, startPage, endPage
    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;

        // 리스트 화면 하단 부분에 나타내는 페이지를 처리하기 위한 데이터들을 계산
        // 전체 페이지를 계산할 수 있다.
        this.totalPage = (totalRow - 1) / this.perPageRow + 1;
        // totalPage = (int)Math.ceil((double)totalRow/(double)perPageRow); 이렇게 할 수도 있다.
        // startPage, endPage
        this.startPage = (this.page - 1) / this.perGroupPage * this.perGroupPage + 1;
        //this.startPage = ((int)Math.floor((double)(this.page - 1) / (double)this.perGroupPage) *  (this.perGroupPage)) + 1;
        this.endPage = this.startPage + this.perGroupPage - 1;
        // endPage 가 총 페이지 수를 넘을 수 없다.
        if(this.endPage > this.totalPage) this.endPage = this.totalPage;

        this.prevPage = this.page != 1;         // 이전 페이지 존재 여부
        this.nextPage = this.page != this.totalPage;         // 다음 페이지 존재 여부
        this.lastPage = this.endPage < this.totalPage;         // 마지막 페이지 존재 여부
        this.firstPage = this.perGroupPage < this.page;         // 처음 페이지 존재 여부

    }

    public int getPerPageRow() {
        return perPageRow;
    }

    public void setPerPageRow(int perPageRow) {
        this.perPageRow = perPageRow;
    }

    public int getStartRow() {
        return (this.page - 1) * this.perPageRow + 1;
    }

//    public void setStartRow(int startRow) {
//        this.startRow = startRow;
//    }

    public int getEndRow() {
        return this.startRow + this.perPageRow -1;
    }

//    public void setEndRow(int endRow) {
//        this.endRow = endRow;
//    }

    public int getTotalRow() {
        return totalRow;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerGroupPage() {
        return perGroupPage;
    }

    public void setPerGroupPage(int perGroupPage) {
        this.perGroupPage = perGroupPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isPrevPage() {
        return prevPage;
    }

    public void setPrevPage(boolean prevPage) {
        this.prevPage = prevPage;
    }

    public boolean isNextPage() {
        return nextPage;
    }

    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    @Override
    public String toString() {
        return "PageObject{" +
                "perPageRow=" + perPageRow +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", totalRow=" + totalRow +
                ", page=" + page +
                ", perGroupPage=" + perGroupPage +
                ", startPage=" + startPage +
                ", endPage=" + endPage +
                ", totalPage=" + totalPage +
                ", prevPage=" + prevPage +
                ", nextPage=" + nextPage +
                ", firstPage=" + firstPage +
                ", lastPage=" + lastPage +
                '}';
    }
}
