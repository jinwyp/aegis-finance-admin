include ../aegis-docker/bin/Makefile

static:
	@cd frontend-admin/src && npm install && gulp clean build
	@cd frontend-site/src  && gulp clean build
