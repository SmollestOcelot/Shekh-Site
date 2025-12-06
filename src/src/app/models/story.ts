export interface StoryDTO {
  id?: number;
  title: string;
  body: string;
  tags?: string;
  category?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface DoodleDTO {
  id?: number;
  title: string;
  description?: string;
  tags?: string;
  imageUrl: string;
  createdAt?: string;
  updatedAt?: string;
}
