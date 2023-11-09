package controller;

import dao.BooksDAO;
import dao.ProgressDAO;
import dto.BooksDTO;
import dto.ProgressDTO;
import entity.BooksBean;
import entity.ProgressBean;

public class ShowController {
    ProgressDTO pdto;
    ProgressDAO pdao;
    ProgressBean pb;
    BooksDTO bdto;
    BooksDAO bdao;
    BooksBean bb;

    public ShowController() {
        this.pdao = new ProgressDAO();
        this.bdao = new BooksDAO();
        try {
            pdto = pdao.select(); // refact
            bdto = bdao.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bb = bdto.get(0);
        pb = pdto.get(0);
    }

    // 修正 select()でbookidを指定
    public ShowController(int bookId) {
        this.pdao = new ProgressDAO();
        this.bdao = new BooksDAO();
        try {
            pdto = pdao.select();
            bdto = bdao.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bb = bdto.get(bookId);
        pb = pdto.get(bookId);
    }

    public void getBookProgress() {
        this.pdto.get(0);
    }

    // 修正 pdtoはaverage内で新たに生成するべき
    public String average() {
        int sumPage = 0;
        for (int i = 0; i < pdto.size(); i++) {
            pb = pdto.get(i);
            sumPage += pb.getTodayPages();
        }
        return String.valueOf(sumPage / pdto.size());
    }

    public int remain(int tP, int nP) {
        return tP - nP;
    }

    public int totalDays(int bookId) {
        int sumDays = 0;
        for (int i = 0; i < pdto.size(); i++) {
            pb = pdto.get(i);
            sumDays++;
        }
        return sumDays;
    }

    public void changeBook() {
        pdto = pdao.select();
    }

    public int totalPages() {
        int totalPages = 0;
        int todayPages = 0;
        try {
            totalPages = bdao.selectTotalPages(1);
            todayPages = pdao.selectTodayPages(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalPages - todayPages;
    }

}
