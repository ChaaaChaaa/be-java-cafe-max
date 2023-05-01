package kr.codesqaud.cafe.dto.page;

public class StandardPage {
    private int pageNum;
    private int amount;
    private int skip;

    public StandardPage(final int pageNum) {
        this.pageNum = pageNum;
        this.amount = 15;
        this.skip = getSkip();
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getAmount() {
        return amount;
    }

    public int getSkip() {
        return (pageNum - 1) * amount;
    }
}
