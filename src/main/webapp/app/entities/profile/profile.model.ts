import { IUser } from 'app/entities/user/user.model';
import { IPost } from 'app/entities/post/post.model';

export interface IProfile {
  id: number;
  name?: string | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
  folowers?: Pick<IProfile, 'id' | 'name'>[] | null;
  likes?: Pick<IPost, 'id' | 'title'>[] | null;
  folowings?: Pick<IProfile, 'id' | 'name'>[] | null;
}

export type NewProfile = Omit<IProfile, 'id'> & { id: null };
