import { ITag, NewTag } from './tag.model';

export const sampleWithRequiredData: ITag = {
  id: 42372,
  name: 'incremental',
};

export const sampleWithPartialData: ITag = {
  id: 72152,
  name: 'Oficial Sorprendente Expandido',
};

export const sampleWithFullData: ITag = {
  id: 82371,
  name: 'Malta',
};

export const sampleWithNewData: NewTag = {
  name: 'holística next-generation',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
