// Imports for loading & configuring the in-memory web api
import { XHRBackend } from '@angular/http';

import { InMemoryBackendService, SEED_DATA } from 'angular2-in-memory-web-api';
import { InMemoryDataServiceOrder }               from '../mock/api/in-memory-data.service';


import { platformBrowserDynamic }    from '@angular/platform-browser-dynamic';
import { HomeComponent } from '../components/home/home-index';
import { homePageRouterProviders } from './home.routes';


platformBrowserDynamic().bootstrapModule(HomeComponent, [
    homePageRouterProviders,
    { provide: XHRBackend, useClass: InMemoryBackendService }, // in-mem server
    { provide: SEED_DATA, useClass: InMemoryDataServiceOrder }      // in-mem server data
]);

