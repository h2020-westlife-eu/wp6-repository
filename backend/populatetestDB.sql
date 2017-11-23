use springw6;
/* Populate one Admin User which will further create other users for the application using GUI */
/* password: user   */
INSERT INTO APP_USER(id,sso_id, password, first_name, last_name, email)
VALUES (1,'user','$2a$10$q1M2rwLvNFOXiArJAG7OFei49Aj1WJrF6CDveoAOEixUAk5e5uNWW', 'User','User','user@xyz.com'),
(2,'30d14bd81ab385bdccb3286406131f39021ba308@west-life.eu','$2a$10$q1M2rwLvNFOXiArJAG7OFei49Aj1WJrF6CDveoAOEixUAk5e', 'Tomas','Kulhanek','tomas.kulhanek@stfc.ac.uk');

/* Populate JOIN Table */
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT user.id, profile.id FROM APP_USER user, USER_PROFILE profile
  where user.sso_id='user' and profile.type='ADMIN';

/* create demo project */
INSERT INTO PROJECT(user_id,project_name,summary)
    VALUES (2,"Strychnin NMR analysis GLY","This project analyses strychnine and binding sites of glycine receptors"),
    (2,"Strychnin NMR analysis TAS","This project analyses strychnine and binding sites of taste receptors TAS2R10"),
    (2,"Strychnin NMR analysis ACE","This project analyses strychnine and binding sites of acetylcholine receptors"),
    (2,"Strychnin NMR analysis Nic","This project analyses strychnine and binding sites of nicotinic acetylcholine receptors"),
    (2,"Strychnin NMR analysis Mus","This project analyses strychnine and binding sites of muscarinic acetylcholine receptors"),
    (1,"Strychnin NMR analysis GLY","This project analyses strychnine and binding sites of glycine receptors"),
    (1,"Strychnin NMR analysis TAS","This project analyses strychnine and binding sites of taste receptors TAS2R10");

