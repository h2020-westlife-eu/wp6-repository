use springw6;
/* Populate one Admin User and second Demo User */
/* password: user   */
/* Populate USER_PROFILE Table */
INSERT INTO USER_PROFILE(type)
VALUES ('USER');

INSERT INTO USER_PROFILE(type)
VALUES ('ADMIN');

INSERT INTO USER_PROFILE(type)
VALUES ('DBA');

INSERT INTO APP_USER(id,sso_id, password, first_name, last_name, email)
VALUES (1,'user','$2a$10$q1M2rwLvNFOXiArJAG7OFei49Aj1WJrF6CDveoAOEixUAk5e5uNWW', 'User','User','user@xyz.com'),
(2, 'demo@repository.west-life.eu', '$2a$10$q1M2rwLvNFOXiArJAG7OFei49Aj1WJrF6CDveoAOEixUAk5e5uNWW','Demo','User','demo.user@repository.west-life.eu');

/* Populate JOIN Table */
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT user.id, profile.id FROM APP_USER user, USER_PROFILE profile
  where user.sso_id='user' and profile.type='ADMIN';

/* Populate JOIN Table */
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT user.id, profile.id FROM APP_USER user, USER_PROFILE profile
  where user.sso_id='demo@repository.west-life.eu' and profile.type='USER';

/* create demo project */
INSERT INTO PROJECT(user_id,project_name,summary,creation_date)
    VALUES
    (2,"Strychnin NMR analysis GLY","This project analyses strychnine and binding sites of glycine receptors",NOW()),
    (2,"Strychnin NMR analysis TAS","This project analyses strychnine and binding sites of taste receptors TAS2R10",NOW()),
    (2,"Strychnin NMR analysis ACE","This project analyses strychnine and binding sites of acetylcholine receptors",NOW()),
    (2,"Strychnin NMR analysis Nic","This project analyses strychnine and binding sites of nicotinic acetylcholine receptors",NOW()),
    (2,"Strychnin NMR analysis Mus","This project analyses strychnine and binding sites of muscarinic acetylcholine receptors",NOW()),
    (1,"Strychnin NMR analysis GLY","This project analyses strychnine and binding sites of glycine receptors",NOW()),
    (1,"Strychnin NMR analysis TAS","This project analyses strychnine and binding sites of taste receptors TAS2R10",NOW());

INSERT INTO DATASET (id, project_id, type, name, info,summary,uri,creation_date)
  VALUES
  (1,1,'folder','XufWqKa1','1.6 Mb','spectrum of strychnine process with v_noesy_pro.mac (NUTS-Pro) or v_noesy.mac (NUTS-2D)','/files/XufWqKa1/',NOW()),
  (2,1,'folder','XufWqKa2','1.2 Mb','spectrum of strychnine process 2','/files/XufWqKa2/',NOW()),
  (3,1,'folder','XufWqKa3','1.2 Mb','spectrum of strychnine process 3','/files/XufWqKa3/',NOW()),
  (4,2,'folder','XufWqKa1','1.3 Mb','spectrum of strychnine process 4','/files/XufWqKa1/',NOW()),
  (5,2,'folder','XufWqKa2','1.3 Mb','spectrum of strychnine process 5','/files/XufWqKa2/',NOW());
