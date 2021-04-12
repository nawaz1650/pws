INSERT INTO role(
	role_id, name)
	VALUES (1, 'ADMIN');

INSERT INTO pws_user(
    	 created_date,mobile)
    	VALUES (CURRENT_DATE,'7977956098');

INSERT INTO user_role(
        	user_id, role_id)
        	VALUES (1, 1);