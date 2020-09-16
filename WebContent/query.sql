CREATE TABLE t_user(
	i_user INT unsigned PRIMARY KEY AUTO_INCREMENT,
	user_id varchar(30) NOT NULL UNIQUE,
	user_pw varchar(70) NOT NULL,
	salt VARCHAR(30) NOT NULL,
	nm varchar(5) NOT NULL,
	profile_img VARCHAR(50),
	r_dt DATETIME DEFAULT NOW(),
	m_dt DATETIME DEFAULT NOW()
);

Select * FROM t_user;

DROP TABLE t_restaurant;
                    
CREATE TABLE t_restaurant(
	i_rest INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	nm VARCHAR(20) NOT NULL,
	addr VARCHAR(100) NOT NULL,
	lat DOUBLE UNSIGNED NOT NULL,
	lng DOUBLE UNSIGNED NOT NULL,
	cd_category INT UNSIGNED NOT NULL,
	i_user INT UNSIGNED NOT NULL,
	r_dt DATETIME DEFAULT NOW(),
	m_dt DATETIME DEFAULT NOW(),
	FOREIGN KEY (i_user) REFERENCES t_user(i_user)
);


-- 글 디테일 
SELECT A.i_rest, A.i_user, A.nm, A.addr, A.hits, B.val AS cd_category_nm, IFNULL(C.cnt,0) AS cntFavorite
FROM t_restaurant A
LEFT JOIN c_code_d B
ON A.cd_category = B.cd
AND B.i_m = 1
LEFT JOIN (
	SELECT i_rest, COUNT(i_rest) AS cnt
	FROM t_user_favorite
	WHERE i_rest = 10 
	GROUP BY i_rest
) C
ON A.i_rest = C.i_rest
WHERE A.i_rest = 10
;


Select * FROM t_restaurant;

INSERT INTO t_restaurant (nm, addr, lat, lng, cd_category, i_user)
VALUES ('테스트','대구', 1, 2, 1, 1);

CREATE TABLE c_code_m(
	i_m INT UNSIGNED PRIMARY KEY,
	`desc` VARCHAR(30) DEFAULT '',
	cd_nm VARCHAR(10) DEFAULT ''
	);

CREATE TABLE c_code_d(
	i_m INT UNSIGNED,
	cd INT UNSIGNED,
	val VARCHAR(15) NOT NULL,
	PRIMARY KEY(i_m, cd),
	FOREIGN KEY(i_m) REFERENCES c_code_m(i_m)
);
-- 포린키 거는 이유 : 잘못된값을 넣지 않기 위해

CREATE TABLE t_restaurant_recommend_menu (
	i_rest INT UNSIGNED,
	seq INT UNSIGNED,
	menu_nm VARCHAR(20) NOT NULL,
	menu_price INT UNSIGNED NOT NULL,
	menu_pic VARCHAR(50),
	PRIMARY KEY(i_rest, seq),
	FOREIGN KEY(i_rest) REFERENCES t_restaurant(i_rest)
);

CREATE TABLE t_restaurant_menu (
	i_rest INT UNSIGNED,
	seq INT UNSIGNED,
	menu_pic VARCHAR(50),
	PRIMARY KEY(i_rest, seq),
	FOREIGN KEY(i_rest) REFERENCES t_restaurant(i_rest)
);

CREATE TABLE t_user_favorite(
	i_rest INT UNSIGNED,
	i_user INT UNSIGNED,
	r_dt DATETIME DEFAULT NOW(),
	PRIMARY KEY(i_rest, i_user),
	FOREIGN KEY(i_rest) REFERENCES t_restaurant(i_rest),
	FOREIGN KEY(i_user) REFERENCES t_user(i_user)
);

SELECT IFNULL(MAX(seq), 0) + 1, 1, '메뉴1', 10000, NULL
FROM t_restaurant_recommend_menu 
WHERE i_rest = 9;

SELECT * FROM t_restaurant_recommend_menu;

SELECT * FROM t_restaurant;

SELECT * FROM t_user;

DELETE FROM t_restaurant_recommend_menu WHERE i_rest=9 AND seq=1;


-- 조인해서 삭제 (글쓴이의 정보(i_user)가  t_restaurant에 있음 ) 
DELETE A -- A테이블만 지운다는 의미 둘다 지울거면 A, B 쓰면 됨
FROM t_restaurant_recommend_menu A
INNER JOIN t_restaurant Bt_restaurant_menu
ON A.i_rest = B.i_rest
WHERE A.i_rest = 9
AND A.seq = 10
AND B.i_user = 1;

SELECT * FROM t_restaurant_menu;

-- t_restaurant_menu 에 삽입값
INSERT INTO t_restaurant_menu
(i_rest, seq, menu_pic)
SELECT 9, IFNULL(MAX(seq), 0) + 1, 'aaa'
FROM t_restaurant_menu
WHERE i_rest = 9

