import { Component, OnInit } from '@angular/core';
import { CryptoCurrency } from './shared/cryptoCurrency';
import { MatTableDataSource } from '@angular/material';
import { FormControl, Validators } from '@angular/forms';
import { CryptoService } from './shared/crypto.service';

@Component({
  selector: 'app-crypto-list',
  templateUrl: './crypto-list.component.html',
  styleUrls: ['./crypto-list.component.scss']
})
export class CryptoListComponent implements OnInit {
  cryptoCurrencies: CryptoCurrency[] = [];
  displayedColumns = ['id', 'name', 'ticker', 'marketCap', 'numberOfCoins', 'actions'];
  dataSource: MatTableDataSource<CryptoCurrency>;

  formName = new FormControl('', [Validators.required, Validators.minLength(3)]);
  formTicker = new FormControl('', [Validators.required, Validators.minLength(3)]);
  formMarketCap = new FormControl(Number, [Validators.required, Validators.min(1)]);
  formNumberOfCoins = new FormControl(Number, [Validators.required, Validators.min(1)]);
  constructor(private cryptoService: CryptoService) { }

  ngOnInit() {
    this.getCryptoCurrencies();
  }
  getCryptoCurrencies() {
    this.cryptoService.getCrypto().subscribe(data => {this.cryptoCurrencies = data['content'] ;
                                                      this.dataSource = new MatTableDataSource(this.cryptoCurrencies); });
  }

  remove(crypto: CryptoCurrency) {
    this.cryptoService.deleteCrypto(crypto);
    this.refresh();
  }

  addCrypto() {
    if (this.formMarketCap.valid && this.formName.valid &&
      this.formNumberOfCoins.valid && this.formTicker.valid) {
        const crypto = new CryptoCurrency();
        crypto.marketCap = this.formMarketCap.value;
        crypto.name = this.formName.value;
        crypto.numberOfCoins = this.formNumberOfCoins.value;
        crypto.ticker = this.formTicker.value;
        this.cryptoService.addCrypto(crypto);
       }
    this.refresh();
  }

  refresh() {
    this.getCryptoCurrencies();
  }

  getErrorMessage(formcontrol: FormControl) {
    const result = formcontrol.hasError('required') ? 'Geef een waarde op '
      : formcontrol.hasError('minlength') ? 'Minimaal 10 tekens'
      : formcontrol.hasError('min') ? 'Minimaal 1' : '';
    console.log(formcontrol.errors);
    return result;

  }

}
