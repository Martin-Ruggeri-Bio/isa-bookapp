import dayjs from 'dayjs/esm';
import { IProfile } from 'app/entities/profile/profile.model';
import { ITag } from 'app/entities/tag/tag.model';

export interface IPost {
  id: number;
  title?: string | null;
  content?: string | null;
  date?: dayjs.Dayjs | null;
  author?: Pick<IProfile, 'id' | 'name'> | null;
  tags?: Pick<ITag, 'id' | 'name'>[] | null;
  likes?: Pick<IProfile, 'id' | 'name'>[] | null;
}

export type NewPost = Omit<IPost, 'id'> & { id: null };
