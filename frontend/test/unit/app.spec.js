import {App} from '../../src/repositoryapp';

describe('the app', () => {
  it('says hello', () => {
    expect(new App().message).toBe('Hello World!');
  });
});
