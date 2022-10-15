import { IProfile, NewProfile } from './profile.model';

export const sampleWithRequiredData: IProfile = {
  id: 11865,
  name: 'generating solid Subida',
};

export const sampleWithPartialData: IProfile = {
  id: 25782,
  name: 'Extendido',
};

export const sampleWithFullData: IProfile = {
  id: 88241,
  name: 'SQL sinergia feed',
};

export const sampleWithNewData: NewProfile = {
  name: 'soporte',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
