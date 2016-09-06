include ../aegis-docker/bin/Makefile

static:
	@cd frontend-admin/src && npm install && gulp build
	@cd frontend-site/src  && gulp build
