import { Injectable } from '@angular/core';
import { Http, Response, Headers, URLSearchParams} from '@angular/http';
import 'rxjs/add/operator/map';
import { GLOBAL } from './global';

@Injectable()
export class DescargaService {
    public url: string;

    constructor(private _http: Http) {
        this.url = GLOBAL.url_archivo;
    }

    descargar(ruta: string) {
        const params = new URLSearchParams();
        params.set('ruta', ruta);
        return this._http.post(this.url, params);
    }
}
