import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CryptoCurrency } from './cryptoCurrency';

@Injectable({
  providedIn: 'root'
})
export class CryptoService {

  constructor(private http: HttpClient) { }

  public getCrypto() {
    return this.http.get('http://localhost:8080/api/currencies');
  }

  public addCrypto(crypto: CryptoCurrency) {
    return this.http.post('http://localhost:8080/api/currencies', crypto, httpOptions).subscribe();
  }

  public putCrypto(crypto: CryptoCurrency) {
    return this.http.put('http://localhost:8080/api/currencies/' + crypto.id, crypto, httpOptions).subscribe();
  }

  public deleteCrypto(crypto: CryptoCurrency) {
    return this.http.delete('http://localhost:8080/api/currencies/' + crypto.id).subscribe();
  }

}

const httpOptions = {
  headers: new HttpHeaders({
      'content-type': 'application/json',
  })
};
