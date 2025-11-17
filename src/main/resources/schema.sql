CREATE TABLE IF NOT EXISTS onbid_item (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,

    rnum INT,
    plnm_no BIGINT,
    pbct_no BIGINT,
    pbct_cdtn_no BIGINT,
    cltr_no BIGINT,
    cltr_hstr_no BIGINT,
    scrn_grp_cd VARCHAR(20),
    ctgr_full_nm VARCHAR(100),
    bid_mnmt_no VARCHAR(50),
    cltr_nm VARCHAR(200),
    cltr_mnmt_no VARCHAR(100),
    ldnm_adrs VARCHAR(200),
    nmrd_adrs VARCHAR(200),
    ldnm_pnu VARCHAR(50),

    dpsl_mtd_cd VARCHAR(20),
    dpsl_mtd_nm VARCHAR(50),

    bid_mtd_nm VARCHAR(100),

    min_bid_prc BIGINT,
    apsl_ases_avg_amt BIGINT,
    fee_rate VARCHAR(20),

    pbct_begn_dtm VARCHAR(20),
    pbct_cls_dtm VARCHAR(20),

    pbct_cltr_stat_nm VARCHAR(50),
    uscbd_cnt INT,
    iqry_cnt INT,

    goods_nm VARCHAR(300),
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_item 
ON onbid_item (cltr_mnmt_no, pbct_begn_dtm, min_bid_prc);