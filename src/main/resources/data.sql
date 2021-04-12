INSERT INTO role(
	role_id, name)
	VALUES (1, 'ADMIN');

INSERT INTO pws_user(
    	 user_id,created_date,mobile)
    	VALUES (1,CURRENT_DATE,'7977956098');

INSERT INTO user_role(
        	user_id, role_id)
        	VALUES (1, 1);