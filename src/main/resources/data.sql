INSERT INTO public.pws_user(
	user_id, address, created_date, mobile, otp, updated_date)
	VALUES (1, 'wasti emporium', CURRENT_DATE,'7506121984' ,NULL , CURRENT_DATE);
INSERT INTO public.pws_user(
	user_id, address, created_date, mobile, otp, updated_date)
	VALUES (2, 'KHADAVLI', CURRENT_DATE,'7977956098' ,NULL , CURRENT_DATE);
INSERT INTO public.role(
	role_id, name)
	VALUES (1, 'ADMIN');
INSERT INTO public.user_role(
	user_id, role_id)
	VALUES (1, 1);
INSERT INTO public.user_role(
	user_id, role_id)
	VALUES (2, 1);


