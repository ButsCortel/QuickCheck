package com.buts.research;

public class Item {

    private String discrimination_index_eval;
    private String difficulty_value_eval;
    private String discrimination_index;
    private String difficulty_value;
    private float examinees;
    private int highGroup;
    private String key;
    private int itemNum;
    //private String correct_ans;
    private int a = 0;
    private int b = 0;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    
    private int no_resp = 0;
    private int corr_ans = 0;		        
    private int corrlg = 0;
    private int corrhg = 0;
    
     
    public void incA() {
    	a++;
    }
    public void incB() {
    	b++;
    }
    public void incC() {
    	c++;
    }
    public void incD() {
    	d++;
    }
    public void incE() {
    	e++;
    }
    public void incRep() {
    	no_resp++;
    }
    public void incCorr() {
    	corr_ans++;
    }
    public void incCorrHG() {
    	corrhg++;
    }
    public void incCorrLG() {
    	corrlg++;
    }
    public void setExaminees(float examinees) {
    	this.examinees = examinees;
    	this.highGroup = Math.round(examinees/2);
    }
    public void setKey(String key) {
    	this.key = key;
    }
    public void setItemNum(int itemNum) {
    	this.itemNum = itemNum;
    }
    public void setDVEval(String difficulty_value_eval) {
    	this.difficulty_value_eval = difficulty_value_eval;
    }
    public void setDIEval(String discrimination_index_eval) {
    	this.discrimination_index_eval = discrimination_index_eval;
    }
    public void setDV(String dv) {
    	this.difficulty_value = dv;
    }
    public void setDI(String di) {
    	this.discrimination_index = di;
    }
    
    public int getA() {
    	return a;
    }
    public int getB() {
    	return b;
    }
    public int getC() {
    	return c;
    }
    public int getD() {
    	return d;
    }
    public int getE() {
    	return e;
    }
    public int getNoResp() {
    	return no_resp;
    }
    public int getCorr() {
    	return corr_ans;
    }
    public int getCorrHG() {
    	return corrhg;
    }
    public int getCorrLG() {
    	return corrlg;
    }
    public int getCorrDiff() {
    	return corrhg-corrlg;
    }
    public float getExaminees() {
    	return examinees;
    }
    public int gethighGroup() {
    	return highGroup;
    }
    public String getKey() {
    	return key;
    }
    public int getItemNum() {
    	return itemNum;
    }
    public String getDV() {
    	return difficulty_value;
    }
    public String getDI() {
    	return discrimination_index;
    }
    public String getDVE() {
    	return difficulty_value_eval;
    }
    public String getDIE() {
    	return discrimination_index_eval;
    }
    
    
}

