import { LazyLoadingPage } from './app.po';

describe('lazy-loading App', function() {
  let page: LazyLoadingPage;

  beforeEach(() => {
    page = new LazyLoadingPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
