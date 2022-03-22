import { TestBed } from '@angular/core/testing';

import { DefaultSettingsService } from './default-settings.service';

describe('DefaultSettingsService', () => {
  let service: DefaultSettingsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DefaultSettingsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
