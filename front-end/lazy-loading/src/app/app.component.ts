import { Component, OnInit } from '@angular/core';
import { Ejecucion } from './models/ejecucion';
import { CargueService } from './services/cargue.service';
import { EjecucionService } from './services/ejecucion.service';
import { DescargaService } from './services/descarga.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  providers: [CargueService, EjecucionService, DescargaService]
})

export class AppComponent implements OnInit {
  public title = 'Cargue Perezoso';
  public ejecuciones: Ejecucion[];
  public nuevoInput: File;
  public nuevaCedula: number;
  public errorMessage: string;
  constructor(
    private _cargueService: CargueService,
    private _ejecucionService: EjecucionService,
    private _descargaService: DescargaService,
  ) {
  }
  onFileChange(event) {
    const fileList: FileList = event.target.files;
    this.nuevoInput = fileList[0];
  }

  onSubmit() {
    this.errorMessage = '';
    this._cargueService.cargarArchivo(this.nuevaCedula, this.nuevoInput).subscribe(
      response => {
        this.listarHistorialEjecuciones();
        this.nuevaCedula = null;
        this.nuevoInput = null;
      },
      error => {
        const errorMessage = <any>error;
        if (errorMessage != null) {
          this.errorMessage = error._body;
          console.log(error);
        }
      }
    );
  }

  ngOnInit() {
    this.listarHistorialEjecuciones();
  }

  listarHistorialEjecuciones() {
    this._ejecucionService.obtenerEjecuciones().subscribe(
      response => {
        this.ejecuciones = response;
      },
      error => {
        const errorMessage = <any>error;
        if (errorMessage != null) {
          this.errorMessage = error._body;
          console.log(error);
        }
      }
    );
  };

  descargar(event) {
    const nombreRutarchivo = event.target.name;
    this._descargaService.descargar(nombreRutarchivo).subscribe(
      response => {
        const response_data = <any>response;
        const data = response_data._body;
        const blob = new Blob([data], { type: 'text/csv' });
        const url = window.URL.createObjectURL(blob);

        const downloadLink = document.createElement('a');
        downloadLink.href = url;
        const nombreArchivo = nombreRutarchivo.substring(nombreRutarchivo.lastIndexOf('/') + 1);
        downloadLink.download = nombreArchivo;
        document.body.appendChild(downloadLink);
        downloadLink.click();
        document.body.removeChild(downloadLink);
      },
      error => {
        const errorMessage = <any>error;
        if (errorMessage != null) {
          this.errorMessage = error._body;
          console.log(error);
        }
      }
    );
  }

}


